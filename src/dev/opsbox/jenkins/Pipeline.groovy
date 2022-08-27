package dev.opsbox.jenkins

class Pipeline {

    // the container image the used to run the build
    def image
    // variables set as env vars
    def variables = []
    // secrets set as env vars
    def secrets = [:]
    def stages = []

    def script

    def load(def yaml) {
        this.image = yaml.image
        this.loadVariables(yaml.variables)
        this.loadSecrets(yaml.secrets)
        this.loadStages(yaml.stages)

        return this
    }

    def loadStages(def yaml) {
        yaml.each { YamlStage ->
            this.stages.add(new Stage(script: script, image: image).load(YamlStage))
        }
    }

    def loadVariables(def yaml) {
        yaml.each { k, v ->
            this.variables.add("$k=$v")
        }
    }

    def loadSecrets(def yaml) {
        yaml.each { k, v ->
            this.secrets[k] = v
        }
    }
}
