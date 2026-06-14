# Documentation

Docs are authored in this repo and published through `composables.com`.

Author docs under `docs/pages/`. The site currently supports these Composables UI markers:

- `<UiDemo id="button-primary" />` embeds a bundled demo app preview and source.
- `<ComponentSource file="Button.kt" />` embeds a source file from the generated component-source bundle.
- `<ApiReference id="buttons" />` expands API docs generated from Kotlin source.

Docs navigation is configured in `docs/docs.yml`.

Demo and component source attachments are configured in `docs/sources.yml`. Each section defines a `root` and `files`.
The `demos.files` entries map `<UiDemo id="..." />` ids to demo source files, and the `components.files` entries map
`<ComponentSource file="..." />` paths to Kotlin source files.

API reference blocks are configured in `docs/api-reference.yml`. Each key matches an `<ApiReference id="..." />` marker
in a docs page. The `symbols` list controls which Kotlin APIs are rendered and their order. A symbol can be a string, or
an object with a `name` and `values` list when the reference should render only selected companion values in a specific
order:

```yaml
buttons:
  symbols:
    - Button
    - IconButton
    - name: ButtonStyle
      values:
        - Default
        - Primary
        - Secondary
```

API reference copy is authored in Kotlin KDoc, not in `docs/api-reference.yml`. Use the declaration KDoc body for the
symbol description, `@param` tags for parameter descriptions, and KDoc on companion `val`s for value descriptions. This
keeps IDE docs, copied component sources, and website reference docs on the same source of truth.

Code fences can opt into site rendering features with metadata:

````text
```kotlin title="Button.kt" lineNumbers collapsible
```
````

- `title="..."` shows a permanent code-block title bar.
- `lineNumbers` adds line numbers.
- `collapsible` adds expand/collapse controls.

```bash
./gradlew bundleDocs
```

`generateDocsApiReference` writes expanded pages to `build/generated/docs/pages`. `bundleDocs` then copies those pages to
`build/docs-bundle/ui/pages` and copies component sources, including KDoc, to `build/docs-bundle/ui/component-sources`.

`bundleDocs` writes the full docs bundle to `build/docs-bundle/ui`. It includes pages, navigation, demo sources,
component sources, and the bundled demo app.
