This is a custom developed Support Ticket Plugin programmed by @Arctic. Its intended to be an Open-Source Ticket plugin that allows servers to establish an in-game support system.

## Commands

---

> `/create ticket <issue>` 
Description: It creates a new ticket with <issue> as the “Note” property. Permission: `default`
> 

> `/viewtickets` 
Description: Opens the “Open Tickets” GUI which displays all visible tickets. 
Permission: `tickets.admin`
> 

> `/tickets reload` 
Description: Reloads default config values. 
Permission: `tickets.admin`
> 

> `/changeTicketGroup <ticket UUID> <group>` 
Description: Changes the selected ticket to the selected support group. (Tab completes)
Permission: `tickets.changegroup` or `tickets.admin`
> 

## GUI Interactions

---

| Interaction Type | Description |
| --- | --- |
| Left Click | Teleports the user to the location the ticket was created at.  |
| Right Click | Upgrades the Priority Queue
!! This is getting updated to apply/change support group |
| Shift + Left Click | No Effect |
| Shift + Right Click | Closes and Deletes the Ticket |
