---
title: Introduction
description: Composables UI is a collection of modern, fully accessible components for Jetpack Compose and Compose Multiplatform.
---

Jetpack Compose is built on top of Material Compose, a Compose implementation of Google's design language, Material
Design.

Material Compose is a great starting point for building apps with Compose. But it also feels like a component library
designed for building Google apps. It is hard to customize, both in styling and UX. You either need to accept Material's
design choices or rebuild the same components on your own.

At the same time, Jetpack Compose is growing outside of Android, thanks to JetBrains' Compose Multiplatform.

But Material Design feels out of place outside Android, especially on iOS.

Material Compose is also not built with pointer cursor in mind. As a result, apps can feel oversized on desktop and web.

This is the problem Composables UI solves, using the following principles:

- [**Universal**](#universal): components adapt automatically to the device's capabilities, such as touch, mouse, and
  keyboard input.
- [**Open Source, Open Code**](#open-source-open-code): MIT-licensed code that is easy to modify.
- [**Beautiful Defaults**](#beautiful-defaults): we sweat the design details so you can build modern apps for all
  platforms without design skills.
- [**Copy-Pastable**](#copy-pastable): each component is self-contained in a single file, so you can copy them directly.

## Universal

Composables UI is designed to work out of the box on any device and platform. On touch devices, clickable targets are rounder and larger. They feel great to press with any finger size.

When a pointer is available, such as a mouse on desktop, clickable targets are sharper and narrower. They feel right at
home on larger screens.

Composables UI is built on top of Compose Unstyled, instead of Material Compose. As a result, you get a proven,
production-ready, flexible layer on top of Compose Foundation, which does not lock you into our design choices.

## Open Source, Open Code

Composables UI is free for everyone. It is licensed under the MIT license, without any "gotchas" for commercial
projects.

It is built from the ground up so you can customize it and make it yours. The API surface is designed to be easy to
understand, even if you are new to Compose.

The code is yours to copy, modify, and ship.

## Beautiful Defaults

Composables UI is designed to be modern, yet intentionally boring. The goal is to give you components that do not get
in the way of you, your app, or your content.

Use them as they are to build modern-looking apps for any platform. Or make them yours by changing the theme, tokens,
and source code.

## Copy-Pastable

Each component's code is self-contained in a single file.

That makes it easy to add to your codebase without depending on a thousand little files.
