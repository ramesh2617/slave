pipeline {
    agent any
	
	parameters {
	    string(name: 'master', defaultvalue:'https://github.com/ramesh2617/master.git', description:'testing')
	    string(name: 'slave', defaultvalue:'https://github.com/ramesh2617/slave.git', description:'testing')
	}
	
    stages  {	
        stage('Set Git Config'){
            sh 'git config --global user.email "krameshchennai3456@gmail.com"'
            sh 'git config --global user.name "ramesh"'
            sh 'git config --global credential.helper cache'
            sh "git config --global credential.helper 'cache --timeout=3600'"
        }
        stage('Set Git Credentials'){
            git credentialsId: 'b6fc8a44-5b4f-4c05-b741-d7da73334889', url: 'https://github.com/ramesh2617/master.git'
            git credentialsId: 'b6fc8a44-5b4f-4c05-b741-d7da73334889', url: 'https://github.com/ramesh2617/slave.git'
        }

        stage('Syncronize master'){
            sh 'git clone --bare ${https://github.com/ramesh2617/master.git} project1.git'
            dir("master.git") {
               //add a remote repository
               sh 'git remote add --mirror=fetch slave ${https://github.com/ramesh2617/slave.git'
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