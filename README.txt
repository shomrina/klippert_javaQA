ввод опций через пробел внутри кавычек(если опций несколько),
например: mvn clean test -Dbrowser="chrome" -Doptions="window-size=1920,1080 incognito" (для линукса кавычки одинарные)

ПРИМЕР ЗАПУСКА:
mvn clean test -Dbrowser="chrome" -Doptions="start-maximized headless" -Dlogin="log" -Dpassword="pass"

mvn clean test -Dbrowser="chrome" -Doptions="headless" -Dlogin="log" -Dpassword="pass"

При запуске mvn clean test запустится с парамтерами по умолчанию:
        <browser>chrome</browser>
        <options>start-maximized</options>
        <login>vanog59788@frnla.com</login>
        <password>P@55w0rD1</password>