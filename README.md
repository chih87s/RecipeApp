# RecipeApp

Mobile Coding Challenge

## Project Overview
[![API](https://img.shields.io/badge/API-29%2B-yellow.svg?style=flat)](https://developer.android.com/about/versions/10)
[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg)](https://kotlinlang.org)

## Table of Contents
- [Project Overview](#project-overview)
- [Environment Setup](#environment-setup)
- [Features](#features)
- [Screenshots](#screenshots)
- [Architecture](#architecture)
- [Future Enhancements](#future-enhancements)
---

## Environment Setup
To set up the development environment, make sure you have the following installed:
### Android Studio
`Android Studio Ladybug | 2024.2.1 Patch 3`
### Gradle
`Gradle Version 8.9 +`

## Features
- 🚀 **Kotlin as the language**: The app is developed using Kotlin, taking full advantage of Kotlin's features and benefits.

- 🏗️ **Modern Architecture Practices**: Follows clean architecture, implementing the MVVM pattern to manage UI and data

- 🎨 **Jetpack Compose**: The UI is built using Jetpack Compose, offering a clean and efficient way to build UIs.

- 🔄 **Coroutines**: Kotlin Coroutines are used for handling asynchronous tasks.

- 🧳 **Modular Navigation**: The app’s navigation logic is split into its own module, making it easier to test and maintain.

- ⚠️ **Error Snackbar**: Displays a Snackbar with an error message in case of failure.

- 🔄 **Orientation Support**: The app adjusts UI layouts based on the screen orientation, providing different views for portrait and landscape modes.

- 🔌 **Hilt/Dagger2**: Uses Hilt for dependency injection, simplifying component management and testing.

- 🧭 **Navigation Component**: Navigation Component is used for handling screen navigation and data passing.

- 🧪 **Unit Testing**: The app includes unit tests for ViewModel and UseCase, ensuring code reliability.

- ♿  **Accessibility**:  The app is optimized for accessibility, ensuring that visually impaired users can interact with the app, with appropriate content descriptions for UI elements.

## Screenshots

## Architecture
The **Project** employs a **MVVM (Model-View-ViewModel) Clean Architecture** pattern, which facilitates a clear separation of concerns and promotes scalability, maintainability, and testability. This architecture divides the application into distinct layers:

- **UI Layer**: The UI is built using Jetpack Compose, and it automatically adjusts to different screen orientations.

- **Domain Layer**: Handles business logic and interacts with the Repository layer to fetch recipe data.

- **Model Layer**:
  - **Repository**: Provides data sources and handles fetching data from local json file.
  - **Models**:
    -**UI Model**: These models are specifically designed for UI representation.
    -**Remote Model**: These models are used for parsing the data from the provided files.
    -**Mapper**: Convert remote model to ui model

## Future Enhancements

- **Decoupling UI and ViewModel**: Adopt an event-driven approach for handling one-time UI effects like snackbar and dialogs.

- **Error Category Handling**: Enhance error handling strategy to differentiate between different types of error.

- **Pagination Function**: Implementing pagination for loading lists of data incrementally. This would improve performance and provide a smoother experience for users when dealing with large datasets (like recipes)

- **UI Testing**: Implement more comprehensive UI tests to ensure the app's functionality and usability across different devices and screen sizes.

- **Enhanced UI/UX**: Further improve the user interface and experience with animations and transitions.

- **Dark Theme Support**: Add support for dark and light themes to enhance user experience and accessibility based on user preferences.
