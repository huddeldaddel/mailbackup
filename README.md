[![Build Status](https://dev.azure.com/huddeldaddel/Personal%20Projects/_apis/build/status/huddeldaddel.mailbackup?branchName=master)](https://dev.azure.com/huddeldaddel/Personal%20Projects/_build/latest?definitionId=8&branchName=master)

# MailBackup

MailBackup is a small commandline tool that can be used to download emails from an IMAP server and store them as EML 
files on your computer.

## Why use MailBackup instead of Thunderbird / Outlook?

This program saves e-mails as EML files. [EML](http://www.ietf.org/rfc/rfc0822.txt) is a simple text format which is 
supported by many e-mail programs. Under Windows, no additional software needs to be installed to view EML files. The 
files also contain all header information and attachments in a single file.

Thus the export as EML has some advantages over Outlook or Thunderbird data files, which can be exchanged badly. These
are difficult to integrate - and can only be converted with a lot of manual effort when changing the mail client.

## Building the code and running tests

MailBackup is written in [Java](https://openjdk.java.net/projects/jdk/11/) and build with 
[Maven](https://maven.apache.org/). Once these tools are installed and configured, you can use Maven to build the code
 and execute tests. Maven's pom.xml uses the usual targets:

* run `mvn test` to compile and test the code
* run `mvn package` to build the JAR file used to run the program.

## Installation

To run the application you need to have [Java](https://openjdk.java.net/projects/jdk/11/) installed on your machine.
You don't need to build to software yourself - we have the 
[latest release](https://github.com/huddeldaddel/mailbackup/releases/download/v1.0.0/v1.0.0.zip) available for download. 
Now unzip the MailBackup archive at the target location and you are ready to execute the program.

## Usage

Execute either mailbackup.sh (MacOS / Linux) oder mailbackup.ps1 (Windows). Basic usage works like this 
`./mailbackup.ps1 -username <USERNAME> -password <PASSWORD>`. 
There are several parameters that you can use to change the default behaviour:

`mailbackup -username <arg> -password <arg> [-hostname <arg>] [-delete] [-flatten] [-olderThan <arg>] [-out <arg>] 
[-outpattern <arg>] [-port <arg>] [-unencrypted]`
 
 - **username &lt;arg&gt;**: This is the username that will be used to login.
 - **password &lt;arg&gt;**: The password that will be used to login.
 - **hostname &lt;arg&gt;**: Sets the hostname of the IMAP server that hosts the mailbox. This parameter is optional. If
  you do not specify a hostname the program will try to guess it from your username - if that contains a @ sign.
 - **delete**: Be careful: this switch will delete all mails from server once they have been downloaded
 - **flatten**: Default behaviour is to rebuild the folder structure of the server locally. If you use this switch no
 folders will be created locally and all files will be written to the output directory directly. 
 - **olderThan &lt;arg&gt;**: This parameter works as a filter to process messages only if they are older than the date
 specified. The date value is expected to be given formatted as YYYY-MM-DD.
 - **out &lt;arg&gt;**: This changes the directory to write the messages to
 - **outpattern &lt;arg&gt;**: Sets a new naming pattern for the message files. This pattern can include the 
 placeholders *&lt;date&gt;*, *&lt;recipient&gt;*, *&lt;sender&gt;* and *&lt;subject&gt;*
 - **port &lt;arg&gt;**: Sets the port number of the IMAP server
 - **unencrypted**: Use this to use an unencrypted connection to the server (no SSL / TLS encryption)

## Contributing

Please read [CONTRIBUTING](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull 
requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the 
[tags on this repository](https://github.com/huddeldaddel/mailbackup/tags).

## Authors

* **Thomas Werner** - *Original author* - [GitHub](https://github.com/huddeldaddel)

## License

This project is licensed under the GNU GENERAL PUBLIC License 3 - see the [LICENSE](LICENSE.md) file for details

## Acknowledgments

* **Billie Thompson** - *Markdown templates* - [PurpleBooth](https://github.com/PurpleBooth)

