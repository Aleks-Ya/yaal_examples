node {
    env.MY_NAME = 'John'

    withCredentials([usernamePassword(credentialsId: 'credential1', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        sh '''
          echo "SINGE QUOTES. Name: $MY_NAME, Username: $USERNAME, Password: $PASSWORD"
        '''

        // INSECURE (SHOWS WARNING):
        sh """
          echo "DOUBLE QUOTES. Name: ${env.MY_NAME}, Username: $USERNAME, Password: $PASSWORD"
        """
    }

    withCredentials([usernameColonPassword(credentialsId: 'credential1', variable: 'USERPASS')]) {
        sh '''
          echo "Name: $MY_NAME, Username and password: $USERPASS, disclosed: $(echo $USERPASS | sed 's/./&_/g')"
        '''
    }
}