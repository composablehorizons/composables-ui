# Contributing

Thanks for your interest in contributing to Composables UI.

Kindly read this before opening a pull request. Keep changes focused, prefer the smallest fix that fully solves the
problem, and verify your work before handing it off.

If you run into an issue and need help, reach out to [Alex Styl on X](https://x.com/alexstyl).

## Development

### Fork this repo

You can fork this repo by clicking the `Fork` button in the top right corner
or [by clicking here](https://github.com/composablehorizons/composables-ui/fork).

### Clone your fork in your local machine

```bash
git clone https://github.com/your-username/composables-ui.git
```

### Navigate to the project directory

```bash
cd composables-ui
```

### Create a new branch

```bash
git checkout -b my-new-branch
```

## Running the component showcase demo

```bash
./gradlew demo:hotRunJvm --auto
```

## Running the sample app

```bash
./gradlew apps:social:hotRunJvm --auto
```

## Running the CLI locally

```bash
./gradlew :packages:cli:run --args="--help"
```

## Testing

Before opening a pull request, run `./gradlew test` to run all tests locally.

Ensure that all tests are passing locally before submitting a pull request. If you are adding new functionality, make
sure to include tests for it.

## Code formatting

We use `spotless` for code formatting. Make sure to run `./gradlew spotlessCheck` to verify that your code is well
formatted according to the project rules before opening a PR.

Use `./gradlew spotlessApply` to fix any code formatting errors you spot.
