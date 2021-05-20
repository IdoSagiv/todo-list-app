# TodoItems List

An Android exercise for developers teaching how to play around with RecyclerView and Adapter

## Specs
- the screen starts out empty (or with previous tasks)
- every time the user taps the "add TODO item" button:
    * if the edit-text is empty (no input), nothing happens
    * if there is input:
        - a new IN-PROGRESS TodoItem will be created and added to the items list
        - the new TodoItem will be shown as the first item in the Recycler view
        - the edit-text input will be erased
- the "TodoItems" list is shown in the screen
  * every operation that creates/edits/deletes a TodoItem should immediately be shown in the UI
  * the order of the TodoItems in the UI is:
    - all IN-PROGRESS items are shown first. items are sorted by creation time,
      where the last-created item is the first item in the list
    - all DONE items are shown afterwards, no particular sort is needed
  * every item shows a status, title and creation time.
  * DONE items should show the status icon as checked, and the description with a strike-through text
  * IN-PROGRESS items should show the status icon as not checked, and the description text normal
  * upon click on the status icon, flip the TodoItem's state (if was DONE will be IN-PROGRESS, and vice versa)
  * upon long press a delete item popup shows on the screen
  * upon click on the item, na edit screen opens
- when a screen rotation happens (user flips the screen):
  * the UI should still show the same list of TodoItems
  * the edit-text should store the same user-input (don't erase input upon screen change)

## Background:

In this project, we are creating a TODO list app.
The user can insert TodoItems, mark them as DONE or IN-PROGRESS, and delete them.

The exact SPECS can be found at file `MainActivity.java`.

*NOTICE:*
The app implementation is extracted into logic and UI:

Pure-logic should be implemented at `TodoItemsHolderImpl.java`.

UI should be implemented at `MainActivity.java`.  

## To fulfill this exercise:

Take a look at the following files, read all of them, and make sure you understand them before starting to write any code:
* `TodoItemsHolder.java` (interface)
* `TodoItemsHolderImpl.java` (default implementation of the interface)
* `TodoItem` (data class representing a TODO item)
* `MainActivity.java` (screen)

After you understand them, go ahead and implement the needed SPECS as defined in `MainActivity`.

> 🛈 **NOTICE:** \
> You might need to modify classes, add fields, change methods etc etc.  
> Don't be afraid, it's completely ok to modify existing code.

## Tests:

*Logic tests:*
You are expected to add unit & flow tests to `TodoItemsHolderImplTest.java`.
Read the entire file and then implement the TODOs in that file. 

*UI tests:*
You are expected to add a few tests to `MainActivityTest.java`.
Read the entire file and then implement the TODOs in that file.

## Remarks:

Tests implementations should come *last*. Start with logic tests and then continue to UI tests. 
My request to write tests is only an added-value for you to sharp your testing skills.
If you see that the exercise takes too much time to implement even *without* writing the tests,
please **LET ME KNOW** and I will drop the request for tests from the exercise submission.

---

✨ Good luck! ✨