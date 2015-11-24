using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Shiyan1.Controllers
{
    public class InforController : Controller
    {
        // GET: Infor
        public ActionResult Index()
        {
            return View();
        }
        public ActionResult Show()
        {
            ViewBag.Message = "Show.";

            return View();
        }

        public string CheckUsername(string username)
        {
            if (username.Equals("wustzz"))
                return "已注册";
            else return "检查通过";
        }
    }
}