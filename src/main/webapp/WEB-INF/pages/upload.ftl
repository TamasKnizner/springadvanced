<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <title>File upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h1>File upload</h1>
    <#if uploadMessage??>
    <div class="alert alert-primary">
        ${uploadMessage}
    </div>
    </#if>
    <h2>Upload event file</h2>
    <form action="<@spring.url '/uploadEvents'/>" method="POST" enctype="multipart/form-data">
    <div class="form-group">
        <input type="file" class="form-control" id="eventFile" aria-describedby="eventFile" name="file">
    </div>
    <button type="submit" class="btn btn-primary">Upload</button>
    </form>
    <hr>
    <h2>Upload user file</h2>
    <form action="<@spring.url '/uploadUsers'/>" method="POST" enctype="multipart/form-data">
        <div class="form-group">
            <input type="file" class="form-control" id="userFile" aria-describedby="userFile" name="file">
        </div>
        <button type="submit" class="btn btn-primary">Upload</button>
    </form>
    <hr>
    Check saved events and users <a href="<@spring.url '/vardump'/>">HERE</a>
</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</body>
</html>