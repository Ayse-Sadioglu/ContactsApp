# ContactsApp

ContactsApp is an Android application built with Kotlin Jetpack Compose.
The app communicates fully with a remote REST API.

## Features

- Fetch contacts from backend
- Create new contacts
- Optional image upload when creating a contact
- Alphabetical listing
- Swipe actions
- Clean Architecture structure
- Search

## API

The app uses a remote API with the following endpoints:

- GET /api/User/GetAll
- GET /api/User/{id}
- POST /api/User
- PUT /api/User/{id}
- DELETE /api/User/{id}
- POST /api/User/UploadImage


## Development Notes

During development emulator storage and disk space issues caused delays and inconsistent behavior.  

## Current State

The following parts of the app are functional:

- Add contact
- Get all contacts
- Alphabetical sorting
- Working UI screens built with Compose
- Search



