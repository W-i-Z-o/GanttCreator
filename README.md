# GanttCreator
We know, the code is messy – do want to make it beautiful again?

## Precondition for this script to work:
* Use the Excel export of Jira as basis. You have to open it in Excel and resave it as an explicit .xls file, otherwise it won't work.
* You have to maintain Sprints and RUP phases (aka Jira Labels) manually in the Enums LegalSprints/Phases. The order you list it there will be used for the order in MS Project.
* Tasks without labels get ignored.
* Tasks with more than one Sprint/labels get ignored.
* Tasks get assigned to the assignee of Jira (only one person possible).
* Unassigned tasks get assigned to the group aka all other assignees, that ever get assigned to a task.

also see: https://smartcinemaproject.wordpress.com/2016/12/05/gantt-%F0%9F%92%A3%E2%98%A0%F0%9F%94%AB%F0%9F%91%8A%F0%9F%97%B1/
