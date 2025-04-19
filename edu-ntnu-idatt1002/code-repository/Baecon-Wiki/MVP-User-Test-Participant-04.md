John's user test for Wireframes.

## User Test Information

- Test Participant: 4
- Product version: MVP v1
- Date: 26.03.2023
- Facilitator: John

#### Test Participant information
- Gender: Male
- Age: 37
- Level of technical competence: High to Very High ([See level definitions here](https://gitlab.stud.idi.ntnu.no/team_01-idatt1002/project-assignment-idatt1002-y2023_spring-t01/-/wikis/Level-of-Technical-Competence-Definitions))
- Education: Siv.ing. Master's degree in Industrial Design.
- Occupation: UX consultant for major IT consulting firm.


# Test Feedback

### 1. Change overview month:
- Which month am I in now? Only says month in the dropdown, nowhere else suggests what month at start.
Should only show month and year.
- Align dropdown to left


### 2. Find the budget page
- Doesn't look very trustworthy, to many dollar signs, haha.


### 3. Add a new income to the budget
- Why does repeat show when I've not ticked repeat. What does it do when I check it?
- Also checked repeat without selecting anything, so I'm not sure how many it repeats
- Tried clicking. The middle of the combobox.
- Doesn't show the selected repeat on the combobox. Only "Repeat"
- Very critical error! :D
- Food and entertainment income :thumbs_up
- Cannot input with comma decimal, even though it shows comma in the budget view.
 

### 4. Set a recurring income
- Added a recurring income, I think. No feedback given en.


### 5. Add a new expense to the budget
- Same
- Can use the app with an open expense dialog
- Can have two open.
- Should set limit to number of numbers


### 6. Find the accounting page
- Found it
- Which month am I currently viewing?
- Dates indicates march but no "big" indicator.
- Why aren't months as dropdowns?
- No buttons seems to work


### 7. Add a new income to the accounting
- Hjelpetekst vanished when I clicked inside the amount box
- Screen reader may not always catch ghost text
- Empty repeat box. Doesn't repeat.
- Not allow line breaks if you cannot use line breaks.
- Linebreak once, breaks the Description input 
- letters in input breaks the app


### 8. Add new expense to the accounting
- Same same 


### 9. Find the expense from 12.03.2023
- Found it.
- What's the deal with timestamps. Dummy data has a proper time (e.g. 15:34:19), but real data doesn't (i.e. 00:00:00).


### 10. Find your scanned receipts
- Already tried clicking "Receipts" on an Accounting entry. Since that didn't work, user clicked the "Receipts" button in sidebar.
- Previous interface (Accounting) indicated that there should be receipts, since there's a button there, but unable to view them there. The added entries there show a receipt button. Why, when there's no receipts?
- How does search work? Will it override a set time range? Or will it search within the set month
- Filter search?
- The bar hierarchy suggests that the bar on top will search globally, while if it was inside the scroll pane it would search within the defined date/month parameters.
- Looks like global search (which was the intended function).
- Can I filter search results by the month/year? How does search work/filter? Lots of options here, that needs to be weighed.
- "Open File Location" would open where the files are. Risk of users bungling the files, by moving, deleting, etc? Is it neccessary? Advanced option maybe? "Open File Location" is tech level 7.
- Copy to downloads? "Export" or "Save As" receipt instead, then show file dialog.


### 11. Determine whether you made a profit or a loss from the previous month
- Accounting. February. No entries in there. No idea. No sums in accounting.
- Went to budget. Didn't notice the sum hidden in the corner.
- Went to dashboard. Can't find any summary. Would have to use a calculator.


### 12. Add a new category
- Settings. Categories. New. Pretty easy.
- Easy to navigate settings. Straightforward and familiar.


### 13. Edit an existing category
- Works, but doesn't work.


### 14. General feedback
- Dashboard: 
   - Not self explanatory graphs. 
   - Overview looks good, but too much whitespace makes it hard to read. Should also have a summary of the results.
   - Should default to current month.
- Budget:
   - E and D buttons are what? 
   - Does `$$$$` mean total sum?
- Accounting:
   - Maybe use tabs (better overview). 
   - Go dropdown for a better UI layout.
   - Main menu on side, module menu/buttons on top.
- Probably not WCAG compliant ;)


## Facilitator notes

- Participant was acutely aware of his role as "user test participant".
- Participant is himself an experienced UX designer and consultant.
- Participant have a high level of technical skill and aptitude.
- The participant tried testing the application both as a "normal" user (i.e. tried doing things "correct", and from the perspective of someone with a high level of insight (i.e. know how to break input, and attempting to do so).
- The test thus became both a user test and a QA run-through, as with the test on participant [P02-M32](https://gitlab.stud.idi.ntnu.no/team_01-idatt1002/project-assignment-idatt1002-y2023_spring-t01/-/wikis/MVP-User-Test-Participant-02/), which again revealed a lot of weakpoints in both the code, UI and UX.