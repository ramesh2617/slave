pipeline {
    agent any

    environment {
        project1="https://github.com/ramesh2617/master.git"
        project2="https://github.com/ramesh2617/slave.git"
    }    

	parameters {
	    string(name: 'master', description:'testing')
	    string(name: 'slave', description:'testing')
	}
	
    stages  {	
        stage('Set Git Config'){
		  steps {
            sh 'git config --global user.email "krameshchennai3456@gmail.com"'
            sh 'git config --global user.name "ramesh"'
            sh 'git config --global credential.helper cache'
            sh "git config --global credential.helper 'cache --timeout=4500'"
          }
		}  
        stage('Set Git Credentials'){
		  steps{
            git branch: 'main',git credentialsId: 'e8fe7044-14cf-4c53-b693-cc45e142c906', url: 'https://github.com/ramesh2617/master.git'
            git branch: 'main',git credentialsId: 'e8fe7044-14cf-4c53-b693-cc45e142c906', url: 'https://github.com/ramesh2617/slave.git'
          }
		}  

        stage('Syncronize master'){
		  steps {
            sh 'git clone --mirror $https://github.com/ramesh2617/master.git}master.git'
            dir("master.git") {
               //add a remote repository
               sh 'git remote add --mirror=fetch slave ${https://github.com/ramesh2617/slave.git}'
               // update the local copy from the first repository
               sh 'git fetch origin --tags'

               // update the local copy with the second repository
               sh 'git fetch slave --tags'

               // sync back the second repository
               sh 'git push slave --all'
               sh 'git push slave --tags'
		    
			}
			
		  }  
        }
    }
}
