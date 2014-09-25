<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<t:layout>
	<h2>New Message</h2>
	<form id="newmessage" action="newmessagecontroller" method="POST"
		accept-charset="UTF-8">

		<label for="recipient">To</label><br><input type="text"
			id="recipient" name="recipient" value=${param.to}><br><label
			for="subject">Subject</label><br><input type="text"
			id="subject" name="subject"><br><label
      for="body">Body</label><br><textarea id=body name=body form="newmessage"></textarea><br><input type="submit"
			value="Send"><br>
	</form>
</t:layout>