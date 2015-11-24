using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(Shiyan1.Startup))]
namespace Shiyan1
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
