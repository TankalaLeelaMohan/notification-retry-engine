pipeline{
   agent any

   stages{
       stage('Build'){
           steps{
               bat 'mvn clean package'
           }
       }

       stage('Test'){
          steps{
              bat 'mvn test'
          }
       }

       stage('Build Docker Image'){
          steps{
             bat 'docker build -t notification-engine .'
          }
       }

       stage('Run Container'){
           steps{
               bat 'docker rm -f notification-engine-container || exit 0'
               bat 'docker run -d --name notification-engine-container -p 8081:8080 -e DB_URL="jdbc:postgresql://host.docker.internal:5432/notification_engine" notification-engine'
           }
       }
   }
}