from django.db import models

class Examen(models.Model):
	examen_name = models.CharField(max_length=50)
	examen_reden = models.CharField(max_length=500)

	def __unicode__(self):
		return self.examen_name
