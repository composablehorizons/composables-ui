# Composables UI

Copy-pasteable Compose Multiplatform UI components built on top
of [Compose Unstyled](https://composables.com/compose-unstyled).

## Documentation

Visit https://composables.com/ui

Docs are authored in this repo and published through `composables.com`.

Author docs under `docs/pages/`. The site currently supports these Composables UI markers:

- `<UiDemo id="button-primary" />` embeds a bundled demo app preview and source.
- `<ComponentSource file="Button.kt" />` embeds a source file from the generated component-source bundle.
- `<ApiReference id="buttons" />` expands API docs generated from Kotlin source.

Code fences can opt into site rendering features with metadata:

````text
```kotlin title="Button.kt" lineNumbers collapsible
```
````

- `title="..."` shows a permanent code-block title bar.
- `lineNumbers` adds line numbers.
- `collapsible` adds expand/collapse controls.

To publish docs locally:

```bash
./gradlew bundleDocs
cd ../composables.com
npm run import:composables-ui-docs
```

`bundleDocs` writes to `build/docs-bundle/ui`. It also generates a timestamp deployment id for the demo app so `composables.com` can cache-bust the iframe and bundled app assets after import.

The website import script copies the docs, demo app, demo sources, and component sources into `composables.com`.
