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

## Ethical pledge
I pledge the highest level of ethical principles in support of academic excellence. I ensure that all of my work reflects my own abilities and not those of someone else.

✨ Good luck! ✨
