coverallsemma
=============

EMMA to Coveralls report converter. This project is based on EMMA 2.0.5312 release downloaded from Sourceforge (http://sourceforge.net/projects/emma/files/emma-release/2.0.5312/). 

usage
-----
This version of EMMA does only contain the report command. For instrumentation and other functions provided by EMMA please refer to the official build. The report is generated using the same syntax as the offical Emma report tool. Example:

java -cp emmacoveralls.jar emma report -r coveralls -in coverage.em,coverage.ec -sp src/ -Dreport.coveralls.service.jobid=$TRAVIS_JOB_ID -Dreport.coveralls.service.name=travis-ci -Dreport.coveralls.repotoken=$COVERALLS_REPO_TOKEN

license
-------
The original EMMA release is distributed under the terms of Common Public License v1.0 and is thus free for both open-source and commercial development. This modified version is released under the same license.