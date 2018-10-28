$javaargs = @("-jar","./mailbackup-0.1.1.jar")
for ($i = 0; $i -lt $args.count; $i++) {
    $javaargs += '"{0}"' -f $args[$i]
}

Start-Process "java" -NoNewWindow -Wait -ArgumentList $javaargs