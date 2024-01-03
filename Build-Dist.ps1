# Build program using Maven
$mavenProcess = (Start-Process "mvn" -NoNewWindow -PassThru -Wait -ArgumentList @("clean", "package"))
if ($mavenProcess.ExitCode -ne 0) {
    Write-Warning "Failed to build sources. Check Maven output."
    exit
}

# Create dist folder and copy relevant files
New-Item ./target/dist -ItemType Directory | out-null
New-Item ./target/dist/libs -ItemType Directory | out-null
Copy-Item ./target/mailbackup-*.jar ./target/dist/
Copy-Item ./src/main/bash/*.sh ./target/dist/
Copy-Item ./src/main/powershell/*.ps1 ./target/dist/
Copy-Item ~/.m2/repository/com/sun/mail/javax.mail/1.6.2/*.jar ./target/dist/libs/
Copy-Item ~/.m2/repository/commons-cli/commons-cli/1.4/*.jar ./target/dist/libs/
Copy-Item ~/.m2/repository/javax/activation/activation/1.1/*.jar ./target/dist/libs/
Copy-Item ./LICENSE.md ./target/dist/

# Create ZIP archive
$jarFileName = Get-ChildItem -Path ./target/ -Filter mailbackup-*.jar -Name
$patternMatches = $jarFileName | Select-String -Pattern 'mailbackup-(.+?).jar'
$version = $patternMatches.matches.groups[1]
Compress-Archive -Path ./target/dist/* -DestinationPath "./target/v$version.zip"
Write-Host "Distribution has been written to ./target/v$version.zip"
