#!/usr/bin/env groovy

import groov.transform.Field

@Field def STEP_NAME = 'postActionRenovate'

void call (Map params = [:]) {

    def script = params.script ?: this
    def stageName = params.stageName ?: env.STAGE_NAME
    def buildStatus = params.buildStatus ?: ''

    if (buildStatus == 'always') {
        return deleteDir()
    }

    if (buildStatus == 'success') {
        return "...All good..."
    }

    if (buildStatus == 'failure') {
        return "...Failed..."
    }
}
