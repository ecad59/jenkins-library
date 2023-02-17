#!/usr/bin/env groovy

import groov.transform.Field

@Field def STEP_NAME = 'customStepRenovate'

void call (Map params = [:]) {

    def script = params.script ?: this
    def stageName = params.stageName ?: env.STAGE_NAME
    def myDockerImage = "renovate/renovate:34"
    def configFile = 'config-all-repositories.js'

    if (params.scan_type == 'ecad59-single-repository') {
        configFile = 'config-custom-repository.js'
        sh " sed -i 's/##CUSTOM-REPO##/${params.custom_repository_name}/g' config-custom-repository.js"
    }

    sh "cp ${configFile} config.js"

    dockerExecute(dockerWorkspace: '/', dockerImage: myDockerImage, script: this) {

        withCredentials ([
            string(credentialsId: 'GIT_TOKEN', variable: 'git_token'),
        ]) {
            sh """
            export RENOVATE_TOKEN="${git_token}"
            node config.js
            """
        }
    }
}
