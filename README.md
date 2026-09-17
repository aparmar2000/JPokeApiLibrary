[![Release](https://img.shields.io/github/v/release/aparmar2000/JPokeApiLibrary?style=flat)](https://github.com/aparmar2000/JPokeApiLibrary/releases)
[![Build](https://github.com/aparmar2000/JPokeApiLibrary/actions/workflows/build.yml/badge.svg)](https://github.com/aparmar2000/JPokeApiLibrary/actions/workflows/build.yml)
[![Java 11](https://img.shields.io/badge/Java-11%2B-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![](https://jitpack.io/v/aparmar2000/JPokeApiLibrary.svg)](https://jitpack.io/#aparmar2000/JPokeApiLibrary)
[![License: LGPL-2.1](https://img.shields.io/github/license/aparmar2000/JPokeApiLibrary)](https://opensource.org/license/lgpl-2-1)
# JPokeApiLibrary

This is a decently-functional Java library that facilitates usage of the [PokéAPI](https://pokeapi.co/) REST API.
It handles most things for you automatically, including rate-limiting, local disk and in-memory caching, typed object deserialization, and streaming/paginated resource retrieval.

## Installing
### Maven
First add JitPack as a repository, since JPokeApiLibrary uses JitPack to distribute dependencies:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Then, add JPokeApiLibrary as a dependency:
```xml
<dependency>
    <groupId>com.github.aparmar2000</groupId>
    <artifactId>JPokeApiLibrary</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle
Add the JitPack repository:
```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
Then add the dependency:
```gradle
dependencies {
    implementation 'com.github.aparmar2000:JPokeApiLibrary:1.0.0'
}
```

## Quickstart
To get started, simply create a new instance of `PokeApiLibrary` with a chosen cache directory:
```java
File cacheDir = new File("pokeapi_cache");
PokeApiLibrary api = new PokeApiLibrary(cacheDir);
```

### Fetching Resources by ID or Name
You can fetch any PokéAPI resource directly:
```java
// Fetch by ID
Pokemon bulbasaur = api.getResourceById(Pokemon.class, 1);
System.out.println("Name: " + bulbasaur.getName());
System.out.println("Base Experience: " + bulbasaur.getBaseExperience());

// Fetch by Name
Pokemon pikachu = api.getResourceByName(Pokemon.class, "pikachu");
System.out.println("Height: " + pikachu.getHeight());
System.out.println("Weight: " + pikachu.getWeight());
```

### Lazy Loading with API Resources
References to other resources in data structures are automatically wrapped in `APIResource<T>` and can be resolved on-demand:
```java
Berry berry = api.getResourceByName(Berry.class, "cheri");
BerryFirmness firmness = berry.getFirmness().resolve();
System.out.println("Firmness: " + firmness.getName());
```

### Pagination & Streams
Retrieve lists or iterate/stream through resources efficiently with built-in pagination:
```java
// Load a list of Pokémon
List<Pokemon> pokemonList = api.getResourceList(Pokemon.class, 25, 100, true, PaginationCacheUsage.USE_CACHE);

// Or stream items lazily
try (Stream<Pokemon> stream = api.getPaginatedResourceStream(Pokemon.class, true, PaginationCacheUsage.USE_CACHE)) {
    stream.limit(50).forEach(p -> System.out.println(p.getName()));
}
```

Additional examples can be found in [the tests](src/test/java/aparmar/pokelibrary).
