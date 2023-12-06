pipeline {
    agent any
    
    stages {
        stage('Run Docker container') {
            steps {
                script {
                    sh 'docker run -d --name=my-new-container1 my-jenkins-image sleep infinity'
                }
            }
        }
        
        stage('Execute commands in new container') {
            steps {
                script {
                    sh 'docker exec my-new-container1 mkdir testdebian'
                    sh 'docker exec my-new-container1 mkdir testdebian/DEBIAN'
                    sh 'docker exec my-new-container1 mkdir testdebian/usr'
                    sh 'docker exec my-new-container1 mkdir testdebian/usr/local'
                    sh 'docker exec my-new-container1 mkdir testdebian/usr/local/bin'
                    sh 'docker exec my-new-container1 wget https://github.com/Parubok01/OS_lab/archive/main.zip'
                    sh 'docker exec my-new-container1 unzip main.zip'
                    sh 'docker exec my-new-container1 mv OS_lab-main/debian/control testdebian/DEBIAN'
                    sh 'docker exec my-new-container1 mv OS_lab-main/calc_files.sh testdebian/usr/local/bin/'
                    sh 'docker exec my-new-container1 dpkg-deb --build testdebian'
                }
            }
        }
        stage('Install Debian Package') {
            steps {
                script {
                    sh 'docker exec my-new-container1 dpkg -i testdebian.deb'
                    sh 'docker exec my-new-container1 chmod +x /usr/local/bin/calc_files.sh'
                    sh 'docker exec my-new-container1 calc_files.sh --check_dir=OS_lab-main'
                    sh 'docker exec my-new-container1 rm -r OS_lab-main'
                }
            }
        }
    }
    
    post {
        always {
            sh 'docker stop my-new-container1 && docker rm my-new-container1'
        }
    }
}