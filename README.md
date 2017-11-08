# [Red Dog](https://www.reddog.mx/)'s Renderer Test API

API from which renderer implementations are expected to use in its unit tests.

Must be added as a dependency with a `test` scope to the `pom.xml` file of the renderer implementation (e.g. look at [rdap-json-renderer `pom.xml` file](https://github.com/NICMx/rdap-json-renderer/blob/master/pom.xml)), depends on JUnit as well:

```xml
<dependency>
	<groupId>mx.nic.rdap</groupId>
	<artifactId>rdap-renderer-test-api</artifactId>
	<version>1.0.0</version>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>junit</groupId>
	<artifactId>junit</artifactId>
	<version>4.12</version>
	<scope>test</scope>
</dependency>
```

## Documentation

The general documentation can be seen [here](https://www.reddog.mx/documentation.html), within the docs there's more specific documentation that can be seen at [Unit testing of Renderer implementation](https://www.reddog.mx/renderer-implementation.html#unit-testing-of-renderer-implementation).

## Contact

Any comment or feedback is welcomed, issues can be reported at Red Dog's [Github corner](https://github.com/NICMx/rdap-renderer-test-api/issues).

Visit our [contact page](https://www.reddog.mx/contact.html) to get more information.

## License

Red Dog is licensed under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).