import com.atlassian.bamboo.specs.api.BambooSpec;
import com.atlassian.bamboo.specs.api.builders.BambooKey;
import com.atlassian.bamboo.specs.api.builders.BambooOid;
import com.atlassian.bamboo.specs.api.builders.permission.PermissionType;
import com.atlassian.bamboo.specs.api.builders.permission.Permissions;
import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.PlanIdentifier;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchCleanup;
import com.atlassian.bamboo.specs.api.builders.plan.branches.PlanBranchManagement;
import com.atlassian.bamboo.specs.api.builders.plan.configuration.ConcurrentBuilds;
import com.atlassian.bamboo.specs.api.builders.project.Project;
import com.atlassian.bamboo.specs.builders.task.ScriptTask;
import com.atlassian.bamboo.specs.util.BambooServer;

@BambooSpec
public class PlanSpec {
    
    public Plan plan() {
        final Plan plan = new Plan(new Project()
                .oid(new BambooOid("o8et7z0jo26a"))
                .key(new BambooKey("PRJ"))
                .name("Project Name"),
            "Plan Name",
            new BambooKey("PLANKEY"))
            .oid(new BambooOid("o8540dnbu9s5"))
            .description("Plan created from (enter repository url of your plan)")
            .pluginConfigurations(new ConcurrentBuilds())
            .stages(new Stage("Stage 1")
                    .jobs(new Job("Build and Run",
                            new BambooKey("RUN"))
                            .tasks(new ScriptTask()
                                    .inlineBody("echo here we go again"))))
            .planBranchManagement(new PlanBranchManagement()
                    .delete(new BranchCleanup()))
            .forceStopHungBuilds();
        return plan;
    }
    
    public PlanPermissions planPermission() {
        final PlanPermissions planPermission = new PlanPermissions(new PlanIdentifier("PRJ", "PLANKEY"))
            .permissions(new Permissions()
                    .userPermissions("admin", PermissionType.EDIT, PermissionType.VIEW, PermissionType.ADMIN, PermissionType.CLONE, PermissionType.BUILD)
                    .groupPermissions("bamboo-admin", PermissionType.ADMIN, PermissionType.BUILD, PermissionType.CLONE, PermissionType.VIEW, PermissionType.EDIT)
                    .loggedInUserPermissions(PermissionType.VIEW)
                    .anonymousUserPermissionView());
        return planPermission;
    }
    
    public static void main(String... argv) {
        //By default credentials are read from the '.credentials' file.
        BambooServer bambooServer = new BambooServer("http://192.168.31.63:8085");
        final PlanSpec planSpec = new PlanSpec();
        
        final Plan plan = planSpec.plan();
        bambooServer.publish(plan);
        
        final PlanPermissions planPermission = planSpec.planPermission();
        bambooServer.publish(planPermission);
    }
}
