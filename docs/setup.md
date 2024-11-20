# Setup do projeto

1. Novo projeto maven.
2. Adicionar dependências da AWS no `pom.xml`.

```xml
<dependencies>
    <dependency>
        <groupId>com.amazonaws</groupId>
        <artifactId>aws-lambda-java-core</artifactId>
        <version>1.2.1</version>
    </dependency>

    <dependency>
        <groupId>com.amazonaws</groupId>
        <artifactId>aws-lambda-java-log4j2</artifactId>
        <version>1.4.0</version>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.34</version>
        <scope>provided</scope>
    </dependency>

     <!-- lib para deserialização de objetos -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.12.3</version>
    </dependency>
</dependencies>
```

3. Classe principal deve implementar `com.amazonaws.services.lambda.runtime.RequestHandler`

```java
public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {

   @Override
   public Map<String, String> handleRequest(Map<String, Object> stringObjectMap, Context context) {
      return Map.of();
   }
}
```

4. Incluir no `pom.xml` a seção `build` para que quando o código for para a AWS ele "entenda" que precisa baixar as libs externas pelo maven.

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.2.4</version>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                    <configuration>
                        <createDependencyReducedPom>false</createDependencyReducedPom>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
