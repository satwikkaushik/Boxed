# Boxed

Boxed is a Java-based tool designed to streamline the process of managing file archives. It offers a wide range of functionalities, including the creation, extraction, listing, and updating of archives, making it an indispensable utility for efficient file management.

## Features

- **Archive Creation & Extraction**: Easily create new archives or extract existing ones with support for custom file headers and separators to ensure archive integrity.
- **File Listing & Addition**: List the contents of archives and add new files to existing archives without the need for extraction.
- **Command-Line Interface**: A user-friendly command-line interface (CLI) designed for seamless interaction with the archival system, complete with detailed help commands for ease of use.

## Getting Started

### Prerequisites
- Java JDK 11 or later

### Usage

Box can be used by running the `Boxed` command in terminal. 
Following are the supported commands:

- `Boxed box <directory-name>`: Create a new archive.
- `Boxed add <file-path> <archive-name>`: Add a file to an existing archive.
- `Boxed unbox <archive-name>`: Extract files from an archive.
- `Boxed list <archive-name>`: List the contents of an archive.

## Future Works
- Encryption of files
- Support for archiving of directories containing sub-directories
