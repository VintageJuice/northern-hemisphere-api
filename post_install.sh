#!/bin/bash

set -e
set -u

echo ...running post installation env config.

JAR_INSTALL_LOCATION=$(pwd)/target/

echo ...found install location: "$JAR_INSTALL_LOCATION"
cd "$JAR_INSTALL_LOCATION"
JAR_NAME=$(find . -name "*.jar" -type f | sed "s|^\./||")
echo ...found northern-hemisphere-api output jar: "$JAR_NAME"

COMMAND_FILE="$HOME/bin/nhapi"

echo ...checking for existing startup command file
if [ ! -f "$COMMAND_FILE" ]; then
  echo ...no command file found! Installing...
  {
    echo "#!/bin/bash"
    echo "set -e"
    echo "set -u"
    echo "java -jar $JAR_INSTALL_LOCATION$JAR_NAME"
  } >> "$HOME/bin/nhapi"

  chmod +x "$HOME/bin/nhapi"
  echo ...install complete - you can now run the api from your bash shell via nhapi.
else
  echo startup command file already available - skipping file write.
fi



