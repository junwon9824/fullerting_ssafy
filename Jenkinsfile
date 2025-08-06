def component = [
    'front': true,
    'back': true,
    'nginx': true,
    'ai': true,
    

]

// ?åå?ùº Î≥µÏÇ¨ ?ûë?óÖ?ùÑ ?àò?ñâ?ïò?äî ?ï®?àò ?†ï?ùò
def fileCopy() {
    // ?åå?ùº Î≥µÏÇ¨ ?ûë?óÖ ?àò?ñâ
    sh 'cp /var/jenkins_home/workspace/fullerting/submodule/*.yml /var/jenkins_home/workspace/fullerting/backend/src/main/resources'
    sh 'cp /var/jenkins_home/workspace/fullerting/submodule/.env /var/jenkins_home/workspace/fullerting/frontend'
}

pipeline {
    agent any
    environment {
        // ?ôòÍ≤ΩÎ???àò ?Ñ§?†ï
        NGINX_TAG = 'latest'
        FRONT_TAG = 'latest'
        BACK_TAG = 'latest'
        REDIS_TAG = 'alpine'
        DOCKER_USER_ID = 'junwon1131'
        // Docker Hub Î∞? GitHub ?Å¨Î¶¨Îç¥?Öú ID
        DOCKER_HUB_CREDENTIALS_ID = 'Docker-hub'
        GITHUB_CREDENTIALS_ID = 'Github-access-token'
        GITLAB_CREDENTIALS_ID = 'GitLab-access-token' // GitLab ?Å¨Î¶¨Îç¥?Öú ID Ï∂îÍ??
        REPO = 's10-ai-image-sub2/S10P12C102'
        GIT_REPO = 'https://github.com/junwon9824/fullertingsecretfolder.git'


        // Gradle ?ôòÍ≤? Î≥??àò ?Ñ§?†ï
        ORG_GRADLE_JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
    }

    stages {
//         stage('Setup Environment') {
//             steps {
//                 dir("${env.WORKSPACE}/back") {
//                     script {
//                         sh 'ls . -al'
//                         // ?Öå?ä§?ä∏?ö© ?âò ÏΩîÎìú Ï∂îÍ??
//                         sh 'echo "This is a test shell script"'
//
//   // ?ãú?Å¨Î¶? ?åå?ùº ?Ç¨?ö©
//
//
//
//                     }
//                 }
//             }
//         }


        stage('Checkout') {
            steps {
                script {
                    sh 'pwd'
                    sh 'ls -al'

                    dir('submodule')
                    {

                        // GitHub access token?ùÑ ?Ç¨?ö©?ïò?ó¨ submodule?ùÑ Í∞??†∏?ò¥
                        checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [[$class: 'SubmoduleOption', disableSubmodules: false, parentCredentials: true, recursiveSubmodules: true, reference: '', trackingSubmodules: false]], userRemoteConfigs: [[credentialsId: 'Github-access-token', url: GIT_REPO]]])
//                         sh 'git pull origin main'
                        sh 'echo "This is a test submodule script"'
//                         sh 'rm .env'
                        sh 'cat  application.yml'
                        sh 'pwd'
                        sh 'ls -al'

                    }

                      sh 'pwd'
                      sh 'ls -al'
                }
            }
        }

        stage('Copy Files') {
                    steps {
                        script {
                            // ?åå?ùº Î≥µÏÇ¨ ?îîÎ≤ÑÍπÖ Î©îÏãúÏß?
                            echo "Copying YAML files from submodule to src/main/resources..."

                            // ?åå?ùº Î≥µÏÇ¨ ?ûë?óÖ ?àò?ñâ
                            fileCopy()

                            // ?åå?ùº Î≥µÏÇ¨ ?ôÑÎ£? ?îîÎ≤ÑÍπÖ Î©îÏãúÏß?
                            echo "Copying completed."
                        }
                    }
                }

        stage('Build') {
            steps {
                script {
                    // ?òÑ?û¨ ?îî?†â?Ü†Î¶? ?úÑÏπòÏ?? ?åå?ùº Î™©Î°ù Ï∂úÎ†•?ùÑ backend ?îî?†â?Ü†Î¶? ?Ç¥?óê?Ñú ?ã§?ñâ
                    dir('backend') {
                        sh 'pwd'  // ?òÑ?û¨ ?îî?†â?Ü†Î¶? ?úÑÏπ? Ï∂úÎ†•
                        sh 'ls -al'  // ?îî?†â?Ü†Î¶? ?Ç¥?ùò ?åå?ùº Î™©Î°ù Ï∂úÎ†•

                        // docker-composeÍ∞? ?Ñ§ÏπòÎêò?ñ¥ ?ûà?äîÏß? ?ôï?ù∏?ïòÍ≥?, ?óÜ?úºÎ©? ?Ñ§Ïπ?
                        sh '''
                        if ! command -v docker-compose &> /dev/null
                        then
                            echo "docker-compose not found, installing..."
                            sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
                            sudo chmod +x /usr/local/bin/docker-compose
                        else
                            echo "docker-compose is already installed."
                        fi
                        '''

                        // Docker ComposeÎ•? ?Ç¨?ö©?ïò?ó¨ ?ÑúÎπÑÏä§ ÎπåÎìú
                        sh 'docker-compose -f docker-compose.yml build'
                    }
                }
            }
        }

        stage('Docker Login') {
            steps {
                // Docker Hub ?Å¨Î¶¨Îç¥?Öú?ùÑ ?Ç¨?ö©?ïò?ó¨ Docker?óê Î°úÍ∑∏?ù∏
                withCredentials([usernamePassword(credentialsId: 'Docker-hub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'echo $DOCKER_PASSWORD | docker login --username $DOCKER_USER --password-stdin'
                }
            }
        }

        stage('Tag and Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'Docker-hub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                     sh 'pwd'
                    sh 'docker-compose -f backend/docker-compose.yml push'
                }
            }
        }

        stage('Prune old images') {
            steps {
                script {
                    sh 'docker image prune --filter until=1h'
                }
            }
        }

        stage('Pull') {
            steps {
                script {
                    component.each { entry ->
                        if (entry.value && entry.key != 'redis') {
                            def var = entry.key
                            sh "docker-compose -f backend/docker-compose.yml -p develop-server pull ${var.toLowerCase()}"
                        }
                    }
                }
            }
        }
        
        stage('Down') {
            steps {
                script {
                    component.each { entry ->
                        if (entry.value) {
                            def var = entry.key
                            try {
                                sh "docker-compose -f backend/docker-compose.yml -p develop-server down ${var.toLowerCase()}"
                            } catch (Exception e) {
                                echo "Failed to down ${var.toLowerCase()}."
                            }
                        }
                    }
                }
            }
        }


        stage('Up') {
            steps {
                script {
                    component.each { entry ->
                        if (entry.value) {
                            def var = entry.key
                            try {
                                sh "docker-compose -f backend/docker-compose.yml -p develop-server up -d ${var.toLowerCase()}"
                            } catch (Exception e) {
                                // 'docker compose up -d' Î™ÖÎ†π?ù¥ ?ã§?å®?ïú Í≤ΩÏö∞
                                echo "Failed to up. Starting 'docker compose start'..."
                                sh "docker-compose -f backend/docker-compose.yml -p develop-server restart ${var.toLowerCase()}"
                            }
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                def Author_ID = sh(script: 'git show -s --pretty=%an', returnStdout: true).trim()
                def Author_Name = sh(script: 'git show -s --pretty=%ae', returnStdout: true).trim()
                mattermostSend(color: 'good',
                    message: "ÎπåÎìú ${currentBuild.currentResult}: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)",
                    endpoint: 'https://meeting.ssafy.com/hooks/pbwfpcrqgff1zr8fmjzq7iukfr',
                    channel: 'C102-jenkins'
            )
            }
        }
    }
}
