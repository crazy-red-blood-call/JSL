import {userStore} from "@/pinia/user";

export const handleSelectionChange = (val: any[]) => {
    const userPina = userStore();
    userPina.setSelectedUserList(val);
}