# Build and Setup

Everything run in an Ubuntu shell because easier management of differnt java versions.

1. clone the repo to a local folder
2. install open jdk 17 and activate it
``` 
sudo apt update && sudo apt install -y openjdk-17-jdk
sudo update-java-alternatives --set java-1.17.0-openjdk-amd64
``` 

3. build the project
`./gradlew build -PchecksumUpdateAll`
packages are located at `src\dist\build\distributions`

adapt the test files in csm folder to your needs and run the jmeter tests