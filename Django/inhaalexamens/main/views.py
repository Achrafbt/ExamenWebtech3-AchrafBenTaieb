from django.shortcuts import render
from django.http import HttpResponse, Http404
from django.template import RequestContext, loader
from django.views import generic

from .models import Examen

class IndexView(generic.ListView):
    template_name = 'main/index.html'
    context_object_name = 'examen_list'

    def get_queryset(self):
        return Author.objects.order_by('examen_name')

def index(request):
	examen_list = Examen.objects.order_by('examen_name')

	#return HttpResponse(output)
	return render(request, 'main/index.html', {'examen_list': examen_list})
