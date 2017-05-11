pipelineJob('Demo_webshop Hourly') {
  definition {
    cpsScm {
      scm {
        git {
          branch('master')
          remote {
            url('ssh://jenkins@rancher-agent-01.shared:29418/demo/webshop.git')
            credentials('6cc6afa8-0869-43f0-91a2-d306642ecafb')
          }
        }
      }
      scriptPath('Jenkinsfile-acceptance')
    }
    triggers {
      scm("H */2 * * *")
    }
  }
  properties{
    buildDiscarder {
      strategy {
        logRotator {
          daysToKeepStr('')
          numToKeepStr('10')
          artifactDaysToKeepStr('')
          artifactNumToKeepStr('')
        }
      }
    }
  }
}

pipelineJob('Demo_webshop Acceptance') {
  definition {
    cpsScm {
      scm {
        git {
          branch('master')
          remote {
            url('ssh://jenkins@rancher-agent-01.shared:29418/demo/webshop.git')
            credentials('6cc6afa8-0869-43f0-91a2-d306642ecafb')
          }
        }
      }
      scriptPath('Jenkinsfile-acceptance-only')
    }
  }
  properties{
    buildDiscarder {
      strategy {
        logRotator {
          daysToKeepStr('')
          numToKeepStr('10')
          artifactDaysToKeepStr('')
          artifactNumToKeepStr('')
        }
      }
    }
  }
}


pipelineJob('Demo_webshop Staging Deploy') {
  definition {
    cpsScm {
      scm {
        git {
          branch('master')
          remote {
            url('ssh://jenkins@rancher-agent-01.shared:29418/demo/webshop.git')
            credentials('6cc6afa8-0869-43f0-91a2-d306642ecafb')
          }
        }
      }
      scriptPath('Jenkinsfile-staging-deploy')
    }
  }
  properties{
    buildDiscarder {
      strategy {
        logRotator {
          daysToKeepStr('')
          numToKeepStr('10')
          artifactDaysToKeepStr('')
          artifactNumToKeepStr('')
        }
      }
    }
  }
}


pipelineJob('Demo_webshop Staging Undeploy') {
  definition {
    cpsScm {
      scm {
        git {
          branch('master')
          remote {
            url('ssh://jenkins@rancher-agent-01.shared:29418/demo/webshop.git')
            credentials('6cc6afa8-0869-43f0-91a2-d306642ecafb')
          }
        }
      }
      scriptPath('Jenkinsfile-staging-undeploy')
    }
  }
  properties{
    buildDiscarder {
      strategy {
        logRotator {
          daysToKeepStr('')
          numToKeepStr('10')
          artifactDaysToKeepStr('')
          artifactNumToKeepStr('')
        }
      }
    }
  }
}

pipelineJob('Demo_webshop Gerrit Review') {
  definition {
    cpsScm {
      scm {
        git {
          branch('$GERRIT_REFSPEC')
          remote {
            url('ssh://jenkins@rancher-agent-01.shared:29418/demo/webshop.git')
            credentials('6cc6afa8-0869-43f0-91a2-d306642ecafb')
            refspec('$GERRIT_REFSPEC:$GERRIT_REFSPEC')
          }
          extensions {
            choosingStrategy {
              gerritTrigger()
            }
          }
        }
      }
      scriptPath('Jenkinsfile')
    }
    triggers {
			  gerrit {
					events {
						patchsetCreated()
						draftPublished()
					}
					project('demo/webshop',  ['ANT:master', 'ANT:releases/*'])
				}
			}
  }
  properties{
    buildDiscarder {
      strategy {
        logRotator {
          daysToKeepStr('')
          numToKeepStr('15')
          artifactDaysToKeepStr('')
          artifactNumToKeepStr('')
        }
      }
    }
  }
}
