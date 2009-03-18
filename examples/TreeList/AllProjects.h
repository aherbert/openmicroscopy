#include <string>
#include <omero/API.h>
#include <omero/System.h>
#include <omero/Collections.h>
#include <omero/templates.h>
#include <omero/model/Project.h>
#include <omero/sys/ParametersI.h>

struct AllProjects {

    static std::vector<omero::model::ProjectPtr> getProjects(omero::api::IQueryPrx query, std::string username) {
        omero::sys::ParametersIPtr p = new omero::sys::ParametersI();
        p->add("name", rstring(username));
        omero::api::IObjectList rv = query->findAllByQuery(
            "select p from Project p where p.details.owner.name = :name", p);
        return omero::cast<omero::model::ProjectPtr>(rv);
    }

};
