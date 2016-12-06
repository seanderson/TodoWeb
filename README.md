# TodoWeb

This Android App is a basic demonstration of low level authentication.  At present is only interacts with localhost:5000 as hosted by a sister Flask application mytodo at https://github.com/seanderson/webserver/wiki

Basic usage:
* install flask and its dependencies
* install postgres
* create todob database and initialize it
(in bash shell)
* source flask/bin/activate
* $(flask) run.py

Run this app, and login to running server.  Your user/tasks in the database should be added just
after the pseudo users.

Point browser to locahost:5000.  Register, login, add some user-task pairs.  Check that they
are in your postgres database.
