# Vacation Planner

## Purpose
Vacation Planner is an Android application designed to help users organize and manage their vacations and associated excursions. The app allows users to add, update, and delete vacations and excursions, ensuring that all details are well-organized and easily accessible.

## Features
- Add, update, and delete vacations with details such as title, hotel, start date, and end date.
- Add sample vacation data.
- Validate dates to ensure the end date is after the start date.
- Set alerts for vacation start and end dates.
- Share vacation details via email, clipboard, or SMS.
- Add, update, and delete excursions associated with vacations.
- Validate excursion dates to ensure they fall within the associated vacation dates.
- Set alerts for excursions.
- Display detailed views of vacations and excursions.
- List all vacations and excursions associated with each vacation.

## Android Version Compatibility
This application is compatible with Android 8.0 (API level 26) and higher.

## How to Operate the Application
1. **Home Screen**:
    - From the home screen, navigate to the list of vacations by hitting the enter button.

2. **List of Vacations**:
    - View all added vacations.
    - Add sample vacations.
    - Tap on a vacation to see its detailed view or to update it.
    - Use the 'Add Vacation' button to create a new vacation.

3. **Detailed Vacation View**:
    - View detailed information about the vacation.
    - Add, Edit or delete the vacation using the respective buttons.
    - Use the 'Add Excursion' button to add excursions to the vacation.
    - View the list of associated excursions.
    - Set notifications for vacation start and end dates by clicking Notify button.
    - Share vacation detail via email.
    - Start and end date validations.
    - Dates can be put in manually or by using date pickers. 

4. **Adding/Editing a Vacation**:
    - Enter the vacation title, hotel, start date, and end date or will trigger warnings.
    - Ensure dates are in the correct format and the end date is after the start date.
    - Save the vacation details.

5. **List of Excursions**:
    - View all excursions associated with a specific vacation.
    - Tap on an excursion to see its detailed view or to update it.
    - Use the 'Add Excursion' or the floating button to create a new excursion.

6. **Detailed Excursion View**:
    - View detailed information about the excursion.
    - Edit or delete the excursion using the respective buttons.
    - Clicking the notify button will set a notification which will be triggered on the excursion date.

7. **Adding/Editing an Excursion**:
    - Enter the excursion title and date.
    - Ensure the date is in the correct format and falls within the associated vacation dates.
    - Save the excursion details.

8. **Alerts**:
    - Alerts will automatically trigger on the start and end dates of vacations, and on the date of each excursion.

## Gitlab Repository
 [Vacation Planner Repository](https://gitlab.com/wgu-gitlab-environment/student-repos/yma14/d308-mobile-application-development-android.git)

---

### Implementation Details

The application is built using the following technologies and frameworks:
- **Room Database**: Used as an abstraction layer over SQLite to manage vacation and excursion data.
- **LiveData and ViewModel**: Ensures data is observed and updated seamlessly across the app.
- **RecyclerView**: Displays lists of vacations and excursions.
- **Navigation Component**: Manages navigation between different screens.
- **WorkManager**: Schedules and manages alerts for vacations and excursions.

### Functional Requirements Breakdown
1. **Vacations Management**:
    - Users can create, update, and delete vacations.
    - Validation ensures vacations with associated excursions cannot be deleted.

2. **Vacation Details**:
    - Title, hotel, start date, and end date fields are included.
    - Dates are validated to ensure correct format and logical sequence.

3. **Excursions Management**:
    - Users can add, update, and delete excursions for each vacation.
    - Validation ensures excursions fall within vacation dates.

4. **Alerts and Sharing**:
    - Users can set alerts for important dates.
    - Sharing functionality is integrated to allow users to share vacation details.

### Screens and Layouts
- **Home Screen**: Initial entry point of the app.
- **Vacations List**: Displays all vacations.
- **Excursions List**: Displays excursions associated with a selected vacation.
- **Detailed Vacation View**: Shows detailed vacation information.
- **Detailed Excursion View**: Shows detailed excursion information.
