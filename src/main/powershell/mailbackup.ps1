$javaargs = @("-jar","./mailbackup-0.1.0.jar")
for ( $i = 0; $i -lt $args.count; $i++ ) {
    $javaargs += $args[$i]
}

Start-Process "java" -NoNewWindow -Wait -ArgumentList $javaargs