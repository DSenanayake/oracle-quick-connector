# oracle-quick-connector
This creates Quick Oracle connection with query executor which returns JSON output that can be used with any JSON compatible language.
# execute with Command Line
```sh
java -jar OracleQueryExecutor.jar hostname port service username password query
```
# Dependencies

| Library | Homepage |
| ------ | ------ |
| ojdbc14 | [http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html] |
| jsonSimple | [https://github.com/fangyidong/json-simple] |

# Usages
- PHP
```sh
    private $host;
    private $port;
    private $service;
    private $username;
    private $password;

    private $connectorPath;

/*
*
* You have to manually Initialize the variables in constructor.
*
*/

private function executeQuery($query){
        if(file_exists($this->connectorPath)) {
            $command = $this->getCommandString($this->connectorPath, $this->host, $this->port, $this->service, $this->username, $this->password, $query);
            $exec = array();

            //executes the jar file.
            exec($command, $exec);

            $result = null;
            foreach ($exec as $item) {
                $result .= $item;
            }
            // To ignore the stdClass Type, have to go through json decode process twice. :(
            return json_decode(json_encode(json_decode($result),True));
        }else{
            return null;
        }
    }

```
