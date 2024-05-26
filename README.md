You need to buidl [the shared omponets] (https://github.com/hrstoyanov/bld-shared/blob/main/README.md) first.
Then build the web app:
```
$./bld webapp-download webapp-build
```

and run it:
```
$./bld webapp-debug
```

You should be able to test it at http://localhost:8080, and you should be able to debug it with IntelliJ via remote debugging (see the bld InsBizBld.java)
