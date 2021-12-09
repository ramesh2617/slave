pipeline {
    agent any

    environment {
        project1="https://github.com/ramesh2617/project1.git"
        project2="https://github.com/ramesh2617/project2.git"
    }    

	parameters {
	    string(name: 'project1', description:'testing')
	    string(name: 'project2', description:'testing')
	}
	
    stages  {	
        stage('Set Git Config'){
		  steps {
            sh 'git config --global user.email "test@test.com"'
            sh 'git config --global user.name "ramesh"'
            sh 'git config --global credential.helper cache'
            sh "git config --global credential.helper 'cache --timeout=3600'"
          }
		}  
        stage('Set Git Credentials'){
		  steps{
            git credentialsId: '6799b2fc-ca6c-44ad-978f-ea7d8da885fb', url: 'https://github.com/ramesh2617/project1.git'
           git credentialsId: '6799b2fc-ca6c-44ad-978f-ea7d8da885fb', url: 'https://github.com/ramesh2617/project2.git'
          }
		}  

        stage('Syncronize project2'){
		  steps {
            sh 'git clone --mirror ${https://github.com/ramesh2617/project1.git}project1.git'
            dir("project1.git") {
               //add a remote repository
               sh 'git remote add --mirror=fetch project2 ${https://github.com/ramesh2617/project2.git}'
               // update the local copy from the first repository
               sh 'git fetch origin --tags'

               // update the local copy with the second repository
               sh 'git fetch project2 --tags'

               // sync back the second repository
               sh 'git push project2 --all'
               sh 'git push project2 --tags'
		    
			}
			
		  }  
        }
    }
}