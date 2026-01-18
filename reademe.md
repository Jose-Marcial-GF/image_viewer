# Image Viewer

Image visualization project developed for the **Software Engineering II** course.

This application implements a robust and extensible desktop image viewer, strictly designed under the **Model-View-Presenter** architectural pattern and applying **SOLID** principles to ensure clean and maintainable code.

## 🚀 Main Features

* **Clean Architecture:** Strict separation between business logic, user interface, and control.
* **Smooth Transitions (Smooth Sliding):** Custom animation system with inertia physics when changing images (drag and drop).
* **Presentation Mode (Slideshow):** Automatic image changing every 5 seconds (activatable via the "Eye" button).
* **Dynamic Zoom:** Zoom in and out using buttons or the mouse wheel.
* **Mouse Gestures:** Intuitive Drag & Drop displacement.
* **Image Loading:** Efficient reading from the local file system.
* **Modern Design:** Uses the *FlatLaf* library for a dark and minimalist appearance.

## 🏗️ Architecture and Design

The project has been structured following advanced software engineering principles:

### Model-View-Presenter Pattern
* **Model:** Represents the data (`Image`) and persistence (`FileImageStore`). It knows nothing about the view.
* **View:** (`SwingImageDisplay`, `Desktop`) Totally passive. It is only responsible for painting pixels and capturing raw user events. It makes no decisions.
* **Presenter:** (`ImagePresenter`) The brain of the application. It receives events from the view, updates the model, and decides what and how to paint (including animation logic and timers).

### Applied SOLID Principles
* **Single Responsibility Principle (SRP):** Each class has a single reason to change (e.g., `FileImageStore` only reads files, `SwingImageDisplay` only paints).
* **Open/Closed Principle (OCP):** The system is extensible via the **Command** pattern. New functionalities (such as presentation mode) can be added by creating new commands (`PresentCommand`) without modifying the `Desktop` class.
* **Interface Segregation Principle (ISP):** Clear and specific interfaces such as `ImageDisplay` or `ImageStore`.
* **Dependency Inversion Principle (DIP):** High-level modules depend on abstractions (interfaces), not on concrete implementations (e.g., `ImagePresenter` depends on the `ImageDisplay` interface, not `SwingImageDisplay`).

### Design Patterns Used
* **Command Pattern:** To encapsulate button actions (`NextCommand`, `PrevCommand`, `ZoomInCommand`, `PresentCommand`).
* **Observer Pattern:** Reactive communication between the View and the Presenter using functional interfaces (`Shift`, `Released`, `Zoom`).

### 📂 Folder Structure
Ensure you have a folder named `images` in the project root containing `.jpg` or `.png` files.

```text
/project
├── /images            <-- Place your photos here
├── /src
│   └── software.ulpgc.imageviewer...
└── README.md
```

## 👥 Authors
- Jose Marcial Galván Franco

*Disclaimer: **This README** documentation was generated with the assistance of Artificial Intelligence.*