coverallsemma
=============

EMMA to Coveralls report converter. This project is based on EMMA 2.0.5312 release downloaded from Sourceforge (http://sourceforge.net/projects/emma/files/emma-release/2.0.5312/). 

usage
-----
This version of EMMA does only contain the report command. For instrumentation and other functions provided by EMMA please refer to the official build. The report is generated using the same syntax as the offical Emma report tool. Example:

    java -cp emmacoveralls.jar emma report -r coveralls -in coverage.em,coverage.ec -sp src/ -Dreport.coveralls.service.jobid=$SERVICE_JOBID -Dreport.coveralls.service.name=$SERVICE_NAME -Dreport.coveralls.repotoken=$COVERALLS_REPO_TOKEN

Besides the standard parameters the Emma to Coveralls report converter need the following three properties:

* report.coveralls.service.name - The CI service or other environment in which the test suite was run (refer to https://coveralls.io/docs/api_reference for more information)
* report.coveralls.service.jobid - A unique identifier of the job on the service specified by report.coveralls.service.name
* report.coveralls.repotoken - The secret token for your repository, found at the bottom of your repository's page on Coveralls

license
-------
The original EMMA release is distributed under the terms of Common Public License v1.0 and is thus free for both open-source and commercial development. This modified version is released under the same license.