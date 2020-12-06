package gutizia.util;

public class MultiIdItems {
    // in order from new, least worn to broken
    public final static DegradeableItem AHRIMS_STAFF = new DegradeableItem(new int[] {4710, 4862, 4863, 4864, 4865, 4866}, "Ahrim's staff");
    public final static DegradeableItem AHRIMS_HOOD = new DegradeableItem(new int[] {4708, 4856, 4857, 4858, 4859, 4860}, "Ahrim's hood");
    public final static DegradeableItem AHRIMS_ROBETOP = new DegradeableItem(new int[] {4712, 4868, 4869, 4870, 4871, 4872}, "Ahrim's robetop");
    public final static DegradeableItem AHRIMS_ROBESKIRT = new DegradeableItem(new int[] {4714, 4874, 4875, 4876, 4877, 4878}, "Ahrim's robeskirt");

    public final static DegradeableItem KARILS_CROSSBOW = new DegradeableItem( new int[] {4734, 4934, 4935, 4936, 4937, 4938}, "Karil's crossbow");
    public final static DegradeableItem KARILS_COIF = new DegradeableItem(new int[] {4732, 4928, 4929, 4930, 4931, 49832}, "Karil's coif");
    public final static DegradeableItem KARILS_LEATHERTOP = new DegradeableItem(new int[] {4739, 4940, 4941, 4942, 4943, 4944}, "Karil's leathertop");
    public final static DegradeableItem KARILS_LEATHERSKIRT = new DegradeableItem(new int[] {4738, 4946, 4947, 4948, 4949, 4950}, "Karil's leatherskirt");

    public final static DegradeableItem DHAROKS_GREATAXE = new DegradeableItem(new int[] {4718, 4886, 4887, 4888, 4889, 4890}, "Dharok's greataxe");
    public final static DegradeableItem DHAROKS_HELM = new DegradeableItem(new int[] {4716, 4880, 4881, 4882, 4883, 4884}, "Dharok's helm");
    public final static DegradeableItem DHAROKS_PLATEBODY = new DegradeableItem(new int[] {4720, 4892, 4893, 4894, 4895, 4896}, "Dharok's platebody");
    public final static DegradeableItem DHAROKS_PLATELEGS = new DegradeableItem(new int[] {4722, 4898, 4899, 4900, 4901, 4902}, "Dharok's platelegs");

    public final static DegradeableItem TORAGS_HAMMERS = new DegradeableItem(new int[] {4747, 4958, 4959, 4960, 4961, 4962}, "Torags's hammers");
    public final static DegradeableItem TORAGS_HELM = new DegradeableItem(new int[] {4745, 4952, 4953, 4954, 4955, 4956}, "Torags's helm");
    public final static DegradeableItem TORAGS_PLATEBODY = new DegradeableItem(new int[] {4749, 4964, 4965, 4966, 4967, 4968}, "Torags's platebody");
    public final static DegradeableItem TORAGS_PLATELEGS = new DegradeableItem(new int[] {4751, 4970, 4971, 4972, 4973, 4974}, "Torags's platelegs");


    public final static DegradeableItem VERACS_FLAIL = new DegradeableItem(new int[] {4755, 4982, 4983, 4984, 4985, 4986}, "Veracs's flail");
    public final static DegradeableItem VERACS_HELM = new DegradeableItem(new int[] {4753, 4976, 4977, 4978, 4979, 4980}, "Veracs's helm");
    public final static DegradeableItem VERACS_BRASSARD = new DegradeableItem(new int[] {4757, 4988, 4989, 4990, 4991, 4992}, "Veracs's brassard");
    public final static DegradeableItem VERACS_PLATESKIRT = new DegradeableItem(new int[] {4759, 4994, 4995, 4996, 4997, 4998}, "Veracs's plateskirt");

    public final static DegradeableItem GUTHANS_WARSPEAR = new DegradeableItem(new int[] {4726, 4910, 4911, 4912, 4913, 4914}, "Guthan's warspear");
    public final static DegradeableItem GUTHANS_HELM = new DegradeableItem(new int[] {4724, 4904, 4905, 4906, 4907, 4908}, "Guthan's helm");
    public final static DegradeableItem GUTHANS_PLATEBODY = new DegradeableItem(new int[] {4728, 4916, 4917, 4918, 4919, 4920}, "Guthan's platebody");
    public final static DegradeableItem GUTHANS_CHAINSKIRT = new DegradeableItem(new int[] {4730, 4922, 4923, 4924, 4925, 4926}, "Guthan's chainskirt");

    public static DegradeableItem getDegradeableItemContaining(int id) {
        if (AHRIMS_STAFF.contains(id)) {
            return AHRIMS_STAFF;
        }
        if (AHRIMS_HOOD.contains(id)) {
            return AHRIMS_HOOD;
        }
        if (AHRIMS_ROBETOP.contains(id)) {
            return AHRIMS_ROBETOP;
        }
        if (AHRIMS_ROBESKIRT.contains(id)) {
            return AHRIMS_ROBESKIRT;
        }
        if (KARILS_CROSSBOW.contains(id)) {
            return KARILS_CROSSBOW;
        }
        if (KARILS_COIF.contains(id)) {
            return KARILS_COIF;
        }
        if (KARILS_LEATHERTOP.contains(id)) {
            return KARILS_LEATHERTOP;
        }
        if (KARILS_LEATHERSKIRT.contains(id)) {
            return KARILS_LEATHERSKIRT;
        }
        if (DHAROKS_GREATAXE.contains(id)) {
            return DHAROKS_GREATAXE;
        }
        if (DHAROKS_HELM.contains(id)) {
            return DHAROKS_HELM;
        }
        if (DHAROKS_PLATEBODY.contains(id)) {
            return DHAROKS_PLATEBODY;
        }
        if (DHAROKS_PLATELEGS.contains(id)) {
            return DHAROKS_PLATELEGS;
        }
        if (TORAGS_HAMMERS.contains(id)) {
            return TORAGS_HAMMERS;
        }
        if (TORAGS_HELM.contains(id)) {
            return TORAGS_HELM;
        }
        if (TORAGS_PLATEBODY.contains(id)) {
            return TORAGS_PLATEBODY;
        }
        if (TORAGS_PLATELEGS.contains(id)) {
            return TORAGS_PLATELEGS;
        }
        if (VERACS_FLAIL.contains(id)) {
            return VERACS_FLAIL;
        }
        if (VERACS_HELM.contains(id)) {
            return VERACS_HELM;
        }
        if (VERACS_BRASSARD.contains(id)) {
            return VERACS_BRASSARD;
        }
        if (VERACS_PLATESKIRT.contains(id)) {
            return VERACS_PLATESKIRT;
        }
        if (GUTHANS_WARSPEAR.contains(id)) {
            return GUTHANS_WARSPEAR;
        }
        if (GUTHANS_HELM.contains(id)) {
            return GUTHANS_HELM;
        }
        if (GUTHANS_PLATEBODY.contains(id)) {
            return GUTHANS_PLATEBODY;
        }
        if (GUTHANS_CHAINSKIRT.contains(id)) {
            return GUTHANS_CHAINSKIRT;
        }

        return null;
    }
}
