John's user test for Wireframes.

## User Test Information

- Test Participant: 2
- Product version: MVP v1
- Date: 24.03.2023
- Facilitator: John

#### Test Participant information
- Gender: Male
- Age: 32
- Level of technical competence: High to Very high ([See level definitions here](https://gitlab.stud.idi.ntnu.no/team_01-idatt1002/project-assignment-idatt1002-y2023_spring-t01/-/wikis/Level-of-Technical-Competence-Definitions))
- Full stack developer.
- Education: Bachelor's degree in Computer Engineering and in Electronics.
- Occupation: Programmer for major IT consulting firm.

# Test Feedback

### 1. Change overview month:
- Clicked right in the combobox.
- User tried scrolling on the collapsed combox field. User is used to this working in most applications and desktop environments. E.g. KDE, Firefox and various websites.

### 2. Find the budget page
- Found the budget page.

### 3. Add a new income to the budget
- Clicked middle of Repeat combox, no dropdown. Changed color or hover, which usually means that it can be activated where the mouse is hovering.
- Next clicked arrow to get the dropdown.
- Clicked on a Repeat option, but it wasn't selected.
- Had not ticked the repeat box. Repeat combox should be greyed out/unavailable then.
- Should be same combobox on both category name and repeat. They function differently!
- Otherwise OK.
- Tested setting a string as amount. Got error message, which was nice.
- But error should be given inline, not as popup.
- User tested various "wrong" inputs.
- Does not support INPUT in comma separated decimal numbers, as per ISO guidelines.
- Displays with a comma. Should be consistent. I.e. input should take comma, not point.
- Thousand separator is missing. Hard space, as per ISO standard.

### 4. Set a recurring income
- Works, but problem with combobox clickability as mentioned above

### 5. Add a new expense to the budget
- Works, but problem with combobox clickability as mentioned above.
- "What does 'E' and 'D' do"?
- "What do you think they do?"
- "Edit category to expense? D... debit?"
- "Could they be Edit and Delete"?
- "Not very intuitive that this is what they're for. Either get the full text, or a fitting icon. And a Delete button should certainly be clearly marked as such."

### 6. Find the accounting page
- Yes.

### 7. Add a new income to the accounting
- No options in "Repeat".
- Comboboxes worked as expected, except repeat combobox being selectable without "Repeat" being ticked.
- No error message on wrong month input (23-23-1942, 23/23-1942, etc.)
- Unable to add without category. By design.

### 8. Add new expense to the accounting
- Same as 7.
- "Why is timestamp displayed? And why 00:00:00?"
- Cannot add with time included.
- Cannot add with same formatting as is displayed in the accounting page (dd.mm.yyyy), but must use dd/mm/yyyy.
- "Perhaps use a table, with clearly defined rows and columns."

### 9. Find the expense from 12.03.2023
- Yes.
- Clicked the buttons. Nothing happened.
- List not automatically sorted by date. Should be option to chose ascending/descending

### 10. Find your scanned receipts
- Clicked the button besides an accounting entry. Did not work.
- Tried the sidebar button. Found the place they should be.
- Splitpane should not be resizeable, or at least limited.
- "Why does it say `//$$SelectedFileName$$` here?"
- "It's a placeholder. It will display the filname of the currently viewed file."
- "Ok, that makes sense. Even if the current text doesn't."
- Facilitator points the user to the two buttons "Open File Location" and "Download to Downloads Directory":
   - Downloads directory? Which downloads direcotry? User, application, who, where? *I* know what is likely meant by the Downloads Directory (user/Downloads/), but that may not be as clear to everyone.
   - Also, why would I want to copy it to Downloads directory? I have "Open File Location", why would I want to use "Copy to downloads"? Perhaps "Save as" or "Export" would be more descriptive and easier.

### 11. Determine whether you made a profit or a loss from the previous month
- Went to accounting. No sums here.
- "Perhaps the dashboard. Alright, found it."
- Expected to find sums somewhere besides only the dashboard.
- Same for budget. Should be sums there.

### 12. Add a new category
- "Obviously in settings, since I havent seen it anywhere else."
- Found it without issues.
- Expected old name to be in the input field, in grey or something. A ghost.
- Could not press apply/cancel (not yet implemented). Missing cancel buttons on the Expense/Income dialog! Have to use X
- Unable to apply edit to category
- Otherwise OK. Adheres to expected design conventions.

### 13. Edit an existing category
- Worked, but didn't work.

### 14. General feedback
- Very inconsistent design. Pages should look more similar. Except perhaps the Dashboard, since that will collect information from the other pages, and would as such need to be it's own thing.
- Should use tables in accounting.
- Budget was messy.
- Shouldn't there be sums in Accounting, showing the current balance of income and expensed you have put in?
- Noticed a tiny "Last months result" in Budget by accident.
- No graphs in Dashboard (not yet implemented).



## Facilitator notes

- Participant was acutely aware of his role as "user test participant".
- Participant is himself an experienced computer programmer.
- Participant have a high level of technical skill and aptitude.
- The participant is very familiar with what is supposed to be the "correct" inputs, as is expected for users at a certain level of skill. 
- This means that in addition to testing the application as a "normal" user, he intentionally tried to use the application in ways we didn't intend it to be used, in order to produce errors to check what the result would be, e.g. inputting letters instead of numbers.
- Thus the test devolved at times into a QA test, which revaled a lot of weakpoints in both the currenct code, UI and UX, much the same as with participant [P04-M37](https://gitlab.stud.idi.ntnu.no/team_01-idatt1002/project-assignment-idatt1002-y2023_spring-t01/-/wikis/MVP-User-Test-Participant-04).