pipeline {  
  agent any	
  tools { 
   maven 'MAVEN_HOME' 
   jdk 'JAVA_HOME' 
  }
  environment {     
    DOCKERHUB_CREDENTIALS= credentials('manoj2489')     
  }    
  stages {        
    stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
    }	  
    stage('Build Docker Image') {         
      steps{                
	sh 'sudo docker build -t manoj2489/upismart:$BUILD_NUMBER .'           
        echo 'Build Image Completed'                
      }           
    }
    stage('Login to Docker Hub') {         
      steps{                            
	sh 'echo $DOCKERHUB_CREDENTIALS_PSW | sudo docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'                 
	echo 'Login Completed'                
      }           
    }               
    stage('Push Image to Docker Hub') {         
      steps{                            
	sh 'sudo docker push manoj2489/upismart:$BUILD_NUMBER'                 
          echo 'Push Image Completed'       
      }           
    }      
  } //stages 
  post{
    always {  
      sh 'docker logout'           
    }      
  }  
} //pipeline
