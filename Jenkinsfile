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
   }
}