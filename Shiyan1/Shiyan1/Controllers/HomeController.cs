using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Shiyan1.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your application description page.";

            return View();
        }

        public ActionResult Show()
        {
            ViewBag.Message = "show.";

            return View();
        }
        public ActionResult Test()
        {
            ViewBag.Message = "show.";

            return View();
        }


        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }
        public string CheckUsername(string username)
        {
            if (username.Equals("wustzz"))
            {
                return "已注册";
            }
            else
            {
                return "检查通过";
            }
        }
    }
}