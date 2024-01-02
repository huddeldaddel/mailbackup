$jarFileName = Get-ChildItem -Path $PSScriptRoot -Filter mailbackup*.jar -Name
$javaargs = @("-jar", "./$jarFileName")
for ($i = 0; $i -lt $args.count; $i++) {
    $javaargs += '"{0}"' -f $args[$i]
}

Start-Process "java" -NoNewWindow -Wait -ArgumentList $javaargs