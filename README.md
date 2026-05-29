# Composables UI

Copy-pasteable Compose Multiplatform UI components built on top
of [Compose Unstyled](https://composables.com/compose-unstyled).

Visit https://composables.com/ui

## Modules

This repository includes these modules:

- `components`: the copy-pasteable Composables UI components.
- `demo`: a showcase app that contains examples for all Composables UI components.
- `sample`: a sample app that contains an example application built with the components.

Start the desktop demo app:

```bash
./gradlew :demo:hotRunJvm --auto
```

Start the desktop sample app with hot reload:

```bash
./gradlew :sample:hotRunJvm --auto
```

Start the demo app in the browser:

```bash
./gradlew :demo:wasmJsBrowserDevelopmentRun
```

Start the sample app in the browser:

```bash
./gradlew :sample:wasmJsBrowserDevelopmentRun
```

See [CONTRIBUTING.md](CONTRIBUTING.md) for local development and documentation workflow.
